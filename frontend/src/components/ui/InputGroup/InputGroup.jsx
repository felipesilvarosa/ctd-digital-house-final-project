import { useState } from "react"
import iconShow from "src/assets/icon-passwordshow.png"
import iconHide from "src/assets/icon-passwordhide.png"
import styles from "./InputGroup.module.scss"
import { Field } from "formik"

export const InputGroup = ({id, inputType, error, label, style, showPassword, loading, ...otherProps}) => {
  const [ type, setType ] = useState(inputType)
  const [ icon, setIcon ] = useState(iconHide)
  
  const handleToggle = () => {
    setType(curr => curr === "password" ? "text" : "password")
    setIcon(curr => curr === iconHide ? iconShow : iconHide)
  }

  return (
    <div data-testid="input-group" className={styles.Wrapper} style={style}>
      <label htmlFor={id} className={styles.Label}>{label}</label>
      <div className={styles.InputWrapper}>
        <Field type={type} name={id} id={id} className={styles.Input} data-error={!!error} {...otherProps}/>
        { showPassword && <button type="button" onClick={handleToggle} className={styles.ShowPassword}>
          <img src={icon} alt="eye icon" />
        </button> }
        { error && <p className={styles.Error}>{error}</p> }
        <div className={styles.Loading} data-loading={loading} style={{animationDelay: `${Math.floor(Math.random() * 200)}ms`}} />
      </div>
    </div>
  )
}