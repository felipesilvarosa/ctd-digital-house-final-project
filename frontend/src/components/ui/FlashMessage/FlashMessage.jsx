import styles from "./FlashMessage.module.scss"

export const FlashMessage = ({ type, children }) => {
  const typesMap = new Map([
    ["error", styles.Error],
  ])

  const flashClass = typesMap.get(type) ?? styles.Success

  return (
    <div className={[styles.Base, flashClass].join(" ")}>{children}</div>
  )
}