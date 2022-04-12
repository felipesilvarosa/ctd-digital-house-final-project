import { pt } from "date-fns/locale"
import { useState } from "react"
import { DateRange } from "react-date-range";
import { useAuth, useReservations, useWindowSize } from "src/hooks"
import { BaseButton } from "src/components";
import { makeNumberFromDate } from "src/utils";
import "react-date-range/dist/styles.css";
import "react-date-range/dist/theme/default.css";
import "./ProductDetailsAvailability.scss"

export const ProductDetailsAvailability = ({product}) => {
  const size = useWindowSize()
  const { setReservation } = useReservations()
  const { user } = useAuth()

  const unavailableDates = product?.unavailable?.map(date => {
    const chunks = date.split("/")
    return new Date(chunks[0], chunks[1]-1, chunks[2])
  })

  const minDate = () => {
    let dayToReturn = new Date()

    unavailableDates && unavailableDates.forEach(date => {
      if (makeNumberFromDate(date) === makeNumberFromDate(dayToReturn)) {
        dayToReturn.setDate(dayToReturn.getDate()+1)
      }
    })

    return dayToReturn
  }

  const [ ranges, setRanges ] = useState({
    startDate: minDate(),
    endDate: minDate(),
    key: "selection"
  })

  const handleSelect = (range) => {
    setRanges(range.selection)
    setReservation({ product, user, checkIn: range.selection.startDate, checkOut: range.selection.endDate })
  }

  return <>
    <section>
      <h2>Disponibilidade</h2>
      <div className="ProductDetailsCalendar">
        <DateRange 
          direction={size.width > 930 ? "horizontal" : "vertical"}
          weekdayDisplayFormat={size.width > 800 ? "E" : "EEEEE"}  
          locale={pt}
          ranges={[ranges]}
          onChange={handleSelect}
          dateDisplayFormat="d/MMM/yyyy"
          months={2}
          minDate={minDate()}
          disabledDates={unavailableDates}
        />

        <div className="ProductDetailsReserve">
          <p>Escolha as datas desejadas e clique no botão abaixo para iniciar o processo de reserva</p>
          <BaseButton type="link" to={"/reserve/" + product.id}>Reservar</BaseButton>
        </div>
      </div>
    </section>
  </>
}