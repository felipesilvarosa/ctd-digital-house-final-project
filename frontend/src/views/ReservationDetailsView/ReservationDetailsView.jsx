import { useState, useEffect } from "react"
import { useParams } from "react-router";
import { pt } from "date-fns/locale"
import { DateRange } from "react-date-range";

import { 
  SpacingShim, 
  ResponsiveContainer, 
  BackButton, 
  BaseButton,
  StarsMeter,
  ProductDetailsPolicies
} from "src/components"
import { useAuth, useProducts, useReservations, useWindowSize } from "src/hooks"

import styles from "./ReservationDetailsView.module.scss"
import "react-date-range/dist/styles.css";
import "react-date-range/dist/theme/default.css";
import "./ReservationDetailsCalendar.scss"

export const ReservationDetailsView = () => {
  const params = useParams()

  const [ ranges, setRanges ] = useState({
    startDate: new Date(),
    endDate: new Date(),
    key: "selection"
  })

  const handleSelect = (range) => {
    setRanges(range.selection)
  }

  const size = useWindowSize()
  const { user } = useAuth()
  const { findProductById, product, loading: loadingProduct } = useProducts()
  const { setReservation, reservation, loading: loadingReservation } = useReservations()

  useEffect(() => {
    findProductById(params.id)
  }, [])

  useEffect(() => {
    setReservation({ product, user, checkIn: null, checkOut: null })
  }, [product])

  return (
    <>
      <SpacingShim height="4.75rem" />
      <ResponsiveContainer className={styles.ReservationDetails}>
        <BackButton to={"/products/" + params.id}>Voltar</BackButton>
        {
          loadingProduct || loadingReservation || !reservation.product ?
            <h1>Carregando...</h1>
          :
            <>
              <h1 className={styles.ReservationTitle}>{reservation.product.title}</h1>
              <section className={styles.Reservation}>
                <div className={styles.ReservationBody}>
                  <div className={styles.ReservationBlock}>
                    <h2>Complete seus dados</h2>
                    <div className={styles.ReservationCard}>
                      <p>
                        <strong>Nome</strong>
                        <span>{reservation.user.fullName}</span>
                      </p>
                      <p>
                        <strong>E-mail</strong>
                        <span>{reservation.user.email}</span>
                      </p>
                    </div>
                  </div>
                  <div className={styles.ReservationBlock}>
                    <h2>Selecione sua data de reserva</h2>
                    <div className={`${styles.ReservationCard} ReservationDetailsCalendar`}>
                      <DateRange 
                        direction={size.width > 930 ? "horizontal" : "vertical"}
                        weekdayDisplayFormat={size.width > 800 ? "E" : "EEEEE"}  
                        locale={pt}
                        ranges={[ranges]}
                        onChange={handleSelect}
                        dateDisplayFormat="d/MMM/yyyy"
                        months={2}
                        minDate={new Date()}
                      />
                    </div>
                  </div>
                  <div className={styles.ReservationBlock}>
                    <h2>Selecione seu horário de chegada</h2>
                    <div className={styles.ReservationCard}>Horário</div>
                  </div>
                </div>
                <div className={styles.ReservationAside}>
                  <h2>Detalhes da reserva</h2>
                  <img src={reservation.product.images[0]} />
                  <div>
                    <p>Hotel</p>
                    <h3>{reservation.product.title}</h3>
                    {reservation.product.stars && <StarsMeter value={reservation.product.stars} />}
                  </div>
                  <p>{reservation.product.location}</p>
                  <div className={styles.ReservationDates}>
                    <p>
                      <span>Check in</span>
                      <span>23/11/2022</span>
                    </p>
                    <hr />
                    <p>
                      <span>Check out</span>
                      <span>23/12/2022</span>
                    </p>
                  </div>
                  <BaseButton>Reservar</BaseButton>
                </div>
              </section>
            </>
        }
      </ResponsiveContainer>
    </>
  )
}