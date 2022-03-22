import { InnerSearch } from "src/components"
import styles from "./HomeSearchBar.module.scss"

export const HomeSearchBar = (props) => {
  return (
    <div className={styles.TheSearchBar} {...props}>
      <h2>Buscar ofertas em hotéis, casas e muito mais</h2>
      <InnerSearch {...props} />
    </div>
  )
}