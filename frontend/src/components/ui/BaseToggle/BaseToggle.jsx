import styles from "./BaseToggle.module.scss"
import { generateRandomId } from "src/utils"

export const BaseToggle = ({children, onChange, className, id}) => {
  const defaultId = id ?? generateRandomId()
  return <>
    <div className={className ? `${styles.BaseToggle} ${className}` : styles.BaseToggle}>
      <input type="checkbox" onChange={onChange} id={defaultId} />
      <label htmlFor={id}>{children}</label>
    </div>
  </>
}