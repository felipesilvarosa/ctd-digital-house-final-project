import { useNavigate } from "react-router"
import styles from "./BackButton.module.scss"
import { Link } from 'react-router-dom'
export const BackButton = ({children, to, onClick, ...props}) => {
  const navigate = useNavigate()
  const goBack = (event) => {
    if (to) {
      return
    }
    event.preventDefault()
    navigate(-1)
  }
  return <Link className={styles.BackButton} to={to ?? "/"} onClick={goBack} {...props}>{children}</Link>
}