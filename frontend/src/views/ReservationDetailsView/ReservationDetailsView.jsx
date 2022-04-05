import { useState, useEffect } from "react"
import { useParams } from "react-router";
import { useNavigate } from "react-router-dom"
import { pt } from "date-fns/locale"
import { DateRange } from "react-date-range";
import Swal from "sweetalert2"

import { 
  SpacingShim, 
  ResponsiveContainer, 
  BackButton, 
  BaseButton,
  StarsMeter,
  ProductDetailsPolicies,
} from "src/components"
import { useAuth, useProducts, useReservations, useWindowSize } from "src/hooks"
import { makeNumberFromDate } from "src/utils";

import styles from "./ReservationDetailsView.module.scss"
import "react-date-range/dist/styles.css";
import "react-date-range/dist/theme/default.css";
import "./ReservationDetailsCalendar.scss"

export const ReservationDetailsView = () => {
  const checkInTimes = [
    "13:00",
    "14:00",
    "15:00",
    "16:00",
    "17:00",
    "18:00",
    "19:00",
    "20:00",
    "21:00",
    "22:00",
    "23:00"
  ]

  const params = useParams()
  const navigate = useNavigate()

  const size = useWindowSize()
  const { user } = useAuth()
  const { findProductById, product, loading: loadingProduct } = useProducts()
  const { setReservation, clearReservation, reservation, loading: loadingReservation } = useReservations()

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
    startDate: reservation.checkIn ? reservation.checkIn : minDate(),
    endDate: reservation.checkOut ? reservation.checkOut : minDate(),
    key: "selection"
  })

  const handleSelect = (range) => {
    setRanges(range.selection)
    setReservation({ user, product, checkIn: range.selection.startDate, checkOut: range.selection.endDate })
  }

  const handleSubmit = () => {
    Swal.fire(
      'Reserva feita com sucesso!',
      'Você reservou sua hospedagem com sucesso.',
      "success"
    )
    clearReservation()
    navigate("/")
  }

  useEffect(() => {
    findProductById(params.id)
    // eslint-disable-next-line
  }, [params])

  return (
    <>
      <SpacingShim height="4.75rem" />
      <div>
        <ResponsiveContainer className={styles.ReservationDetails}>
          <BackButton to={"/products/" + params.id}>Voltar</BackButton>
          {
            loadingProduct || loadingReservation || !product.id ?
              <h1>Carregando...</h1>
            :
              <>
                <h1 className={styles.ReservationTitle}>{product.title}</h1>
                <section className={styles.Reservation}>
                  <div className={styles.ReservationBody}>
                    <div className={styles.ReservationBlock}>
                      <h2>Complete seus dados</h2>
                      <div className={styles.ReservationCard}>
                        <p>
                          <strong>Nome</strong>
                          <span>{user.fullName}</span>
                        </p>
                        <p>
                          <strong>E-mail</strong>
                          <span>{user.email}</span>
                        </p>
                        <div>
                          <label htmlFor="city">Cidade</label>
                          <input type="text" name="city" id="city" />
                        </div>
                      </div>
                    </div>
                    <div className={styles.ReservationBlock}>
                      <h2>Selecione sua data de reserva</h2>
                      <div className={`${styles.ReservationCard} ReservationDetailsCalendar`}>
                        <DateRange 
                          direction={size.width > 1299 ? "horizontal" : "vertical"}
                          weekdayDisplayFormat={size.width > 800 ? "E" : "EEEEE"}  
                          locale={pt}
                          ranges={[ranges]}
                          onChange={handleSelect}
                          dateDisplayFormat="d/MMM/yyyy"
                          months={2}
                          minDate={minDate()}
                          disabledDates={unavailableDates}
                        />
                      </div>
                    </div>
                    <div className={styles.ReservationBlock}>
                      <h2>Selecione seu horário de chegada</h2>
                      <div className={styles.ReservationCard}>
                        <p className={styles.CheckIn}>
                          <span className="material-icons">{ product.policies.rules.description[0].icon }</span>
                          <span>{ product.policies.rules.description[0].description }</span>
                        </p>
                        <select name="checkInTime">
                          {
                            checkInTimes.map(time => <option key={time} value={time}>{time}</option>)
                          }
                        </select>
                      </div>
                    </div>
                  </div>
                  <div className={styles.ReservationAside}>
                    <div>
                      <h2>Detalhes da reserva</h2>
                      <img src={product.images[0]} alt="foto da acomodação" />
                      <div>
                        <p className={styles.CategoryName}>Hotel</p>
                        <h3>{product.title}</h3>
                        {product.stars && <StarsMeter value={product.stars} />}
                      </div>
                      <p className={styles.Address}>
                        <span className="material-icons">place</span>
                        <span>
                          {product.address}
                        </span>
                      </p>
                      <div className={styles.ReservationDates}>
                        <p>
                          <span>Check in</span>
                          <span>{ranges.startDate.toLocaleDateString("pt-BR")}</span>
                        </p>
                        <hr />
                        <p>
                          <span>Check out</span>
                          <span>{ranges.endDate.toLocaleDateString("pt-BR")}</span>
                        </p>
                      </div>
                      <BaseButton onClick={handleSubmit}>Confirmar Reserva</BaseButton>
                    </div>
                  </div>
                </section>
              </>
          }
        </ResponsiveContainer>
        { product?.policies && <ProductDetailsPolicies policies={product.policies} className={styles.Policies} title="O que você precisa saber" />}
      </div>
    </>
  )
}