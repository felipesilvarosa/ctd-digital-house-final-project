import { Link } from "react-router-dom"
import { useLocation } from "react-router"
import { useAuth } from "src/hooks"
import { useState, useEffect } from "react"
import { makeInitials } from "src/utils"

import styles from "./MenuMobile.module.scss"
import iconFB from "src/assets/icon-facebook.png"
import iconIG from "src/assets/icon-instagram.png"
import iconTW from "src/assets/icon-twitter.png"
import iconLI from "src/assets/icon-linkedin.png"

export const MenuMobile = ({menuItems}) => {
  const { user, userLogout } = useAuth()
  const location = useLocation()

  const socialMedia = [
    {
      id: "fb",
      name: "facebook",
      icon: iconFB,
      to: "/"
    },
    {
      id: "ig",
      name: "instagram",
      icon: iconIG,
      to: "/"
    },
    {
      id: "tw",
      name: "twitter",
      icon: iconTW,
      to: "/"
    },
    {
      id: "li",
      name: "linkedin",
      icon: iconLI,
      to: "/"
    },
  ]

  const [ active, setActive ] = useState(false)
  const [ transition, setTransition ] = useState(false)

  useEffect(() => {
    window.document.body.setAttribute("data-menuopen", active)
    return () => window.document.body.setAttribute("data-menuopen", "false")
  }, [active])

  const toggleTransition = () => {
    setTransition(true)
    setTimeout(() => {
      setTransition(false)
    }, 200)
  }

  const openMenu = () => {
    setActive(true)
    toggleTransition()
  }

  const closeMenu = () => {
    setActive(false)
    toggleTransition()
  }

  const handleLogout = () => {
    userLogout()
    closeMenu()
  }

  return (
    <>
      <button onClick={openMenu} className={styles.Hamburger}>≡</button>
      {
        (active || transition) &&
          <>
            <div className={styles.Backdrop} data-active={active} data-transition={transition} onClick={closeMenu} />
            <div className={styles.Drawer} onClick={e => e.stopPropagation()} data-active={active} data-transition={transition}>
              <button className={styles.CloseMenu} onClick={closeMenu} />
              <div className={styles.Header}>
                {
                  user ?
                    <div className={styles.HeaderContent}>
                      <div className={styles.Avatar}>{ makeInitials(user.fullName) }</div>
                      <p>Olá,</p>
                      <p className={styles.FullName}>{user.fullName}</p>
                    </div>
                  :
                    <h3>Menu</h3>
                }
              </div>
              <div className={styles.Body}>
                {
                  user ?
                    <p className={styles.Logout}>Deseja <button onClick={handleLogout}>encerrar a sessão</button>?</p>
                  :
                    <nav>
                      <ul>
                        {
                          menuItems.length && menuItems.map(item => location.pathname !== item.to && <li key={item.id}><Link onClick={closeMenu} to={item.to}>{item.text}</Link></li>)
                        }
                      </ul>
                    </nav>
                }
              </div>
              <div className={styles.Footer}>
                <ul>
                  {
                    socialMedia.length && socialMedia.map(item => <li key={item.id}><a href={item.to} rel="noreferrer" target="_blank"><img src={item.icon} alt={item.name} /></a></li>)
                  }
                </ul>
              </div>
            </div>
          </>
      }
    </>
  )
}