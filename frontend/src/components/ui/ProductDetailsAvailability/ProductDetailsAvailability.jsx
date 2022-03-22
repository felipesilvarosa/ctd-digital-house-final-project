import { pt } from "date-fns/locale"
import { useState } from "react"
import { DateRange } from "react-date-range";
import { useWindowSize } from "src/hooks"
import { BaseButton } from "src/components";
import "react-date-range/dist/styles.css";
import "react-date-range/dist/theme/default.css";
import "./ProductDetailsAvailability.scss"

export const ProductDetailsAvailability = ({unavailable}) => {
  const size = useWindowSize()
  const [ ranges, setRanges ] = useState({
    startDate: new Date(),
    endDate: new Date(),
    key: "selection"
  })

  const handleSelect = (range) => {
    setRanges(range.selection)
  }

  const unavailableDates = unavailable?.map(date => {
    const chunks = date.split("/")
    return new Date(chunks[0], chunks[1]-1, chunks[2])
  })

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
          minDate={new Date()}
          disabledDates={unavailableDates}
        />

        <div className="ProductDetailsReserve">
          <p>Escolha as datas desejadas e clique no botão abaixo para iniciar o processo de reserva</p>
          <BaseButton>Reservar</BaseButton>
        </div>
      </div>
    </section>
  </>
}