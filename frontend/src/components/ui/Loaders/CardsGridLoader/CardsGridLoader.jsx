import { BaseLoader } from "src/components"
import styles from "./CardsGridLoader.module.scss"
export const CardsGridLoader = ({ variant }) => {
  const classMap = new Map([
    ["categories", styles.Categories],
    ["recomendations", styles.Recomendations],
  ])

  const loaderClasses = [
    styles.Grid,
    classMap.get(variant)
  ].join(" ")

  return (
    <BaseLoader className={loaderClasses}>
      <div />
      <div />
      <div />
      <div />
    </BaseLoader>
  )
}