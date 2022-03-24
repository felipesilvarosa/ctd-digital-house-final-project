import { useState } from "react"
import styles from "./BaseModal.module.scss"
export const BaseModal = ({children, toggleModal}) => {

  const [transition, setTransition] = useState(false)

  const closeModal = () => {
    setTransition(true)
    setTimeout(() => {
      toggleModal()
    }, 200)
  }
  return (
    <>
      <div className={styles.Backdrop} onClick={closeModal} data-transition={transition}>
        <button alt="fechar" />
        <div className={styles.Modal} onClick={e => e.stopPropagation()}>
          {children}
        </div>
      </div>
    </>
  )
}