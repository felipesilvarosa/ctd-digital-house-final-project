
import { useAuth } from "src/hooks"
import { Link } from "react-router-dom"
import { useLocation } from "react-router"
import styles from "./MenuDesktop.module.scss"
import { makeInitials } from "src/utils"
export const MenuDesktop = ({menuItems}) => {
  const { user, userLogout } = useAuth()
  const location = useLocation()

  return (
    <div className={styles.Menu}>
      {
        user ? (
          <div className={styles.UserInfo}>
            <div className={styles.Greet}>
              <div className={styles.Avatar}>{ makeInitials(user.fullName) }</div>
              <p>Olá,</p>
              <p>{user.fullName}</p>
            </div>
            <button onClick={userLogout}>
              <span className="material-icons">
                power_settings_new
              </span>
            </button>
          </div>
        ) : (
          <ul>
            {
              menuItems && menuItems.map(item => location.pathname !== item.to && <li key={item.id}><Link to={item.to}>{item.text}</Link></li>)
            }
          </ul>
        )
      }
    </div>
  )
}