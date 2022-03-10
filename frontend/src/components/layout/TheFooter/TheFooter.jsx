import styles from "./TheFooter.module.scss"
import iconFb from "assets/icon-facebook.png"
import iconIn from "assets/icon-linkedin.png"
import iconTw from "assets/icon-twitter.png"
import iconIg from "assets/icon-instagram.png"

export const TheFooter = () => {
  return (
    <footer data-testid="the-footer" className={styles.TheFooter}>
      <p>©2022 DigitalBooking</p>
      <ul>
        <li><img src={iconFb} alt="ícone do facebook" /></li>
        <li><img src={iconIn} alt="ícone do linkedin" /></li>
        <li><img src={iconTw} alt="ícone do twitter" /></li>
        <li><img src={iconIg} alt="ícone do instagram" /></li>
      </ul>
    </footer>
  )
}