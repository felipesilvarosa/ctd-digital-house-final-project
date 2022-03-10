import styles from "./BaseLoader.module.scss"
export const BaseLoader = ({children, className}) => {
  const classes = [
    styles.Loader,
    className
  ].join(" ")

  return (
    <div className={classes}>
      {children}
    </div>
  )
}