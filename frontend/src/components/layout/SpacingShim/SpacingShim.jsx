import styles from "./SpacingShim.module.scss"
export const SpacingShim = ({height}) => {
  return <div className={styles.Shim} style={{height}}></div>
}