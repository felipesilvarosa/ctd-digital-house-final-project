import { Link } from "react-router-dom"
import { TheMenu } from "components"
import logoImage from "assets/logo-sm.png"
import styles from "./TheHeader.module.scss"

export const TheHeader = () => {
  return (
    <header data-testid="the-header" className={styles.TheHeader}>
      <Link to="/"><img src={logoImage} className={styles.LogoImg} alt="digital booking logo" /></Link>
      <TheMenu />
    </header>
  )
}