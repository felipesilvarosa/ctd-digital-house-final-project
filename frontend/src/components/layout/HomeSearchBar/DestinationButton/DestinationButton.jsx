import styles from "./DestinationButton.module.css"
import iconPin from "src/assets/icon-pin.png"

export const DestinationButton = ({city, country, ...otherProps}) => {
  return (
    <button className={styles.DestinationButton} type="button" {...otherProps}>
      <img src={iconPin} alt="pin de local" />
      <h4>{city}</h4>
      <p>{country}</p>
    </button>
  )
}