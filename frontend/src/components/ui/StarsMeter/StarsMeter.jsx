import styles from "./StarsMeter.module.scss"
export const StarsMeter = ({value, className, ...otherProps}) => {
  const width = value / 5 * 100

  return (
    <div className={className ? `${styles.Stars} ${className}` : styles.Stars} {...otherProps} >
      <div className={styles.Filling} style={{width: `calc(${width}% - 2px)`}} />
    </div>
  )
}