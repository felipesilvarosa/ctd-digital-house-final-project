import styles from "./ProductDetailsUtilities.module.scss"
import { getUtilityMaterialIconString } from "src/utils"

export const ProductDetailsUtilities = ({utilities}) => {

  return <>
    <section className={styles.Utilities}>
      <h2>O que este lugar oferece?</h2>
      <div className={styles.Wrapper}>
        {
          utilities && utilities.map(utility => (
            <div key={utility} className={styles.Tag}>
              <span className="material-icons">{getUtilityMaterialIconString(utility)}</span>
              <p>{utility}</p>
            </div>
          ))
        }
      </div>
    </section>
  </>
}