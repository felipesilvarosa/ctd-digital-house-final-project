import { ConditionalWrapper } from "src/components"
import { Link } from "react-router-dom"
import styles from "./HomeCard.module.scss"

export const HomeCard = ({to, image, horizontal, title, clickable, children}) => {
  const classes = [
    styles.Card,
    horizontal && styles.Horizontal
  ].join(" ")

  return (
    <ConditionalWrapper 
      condition={clickable} 
      wrapper={(children) => <Link to={to ?? "/"} className={classes}>{children}</Link>}
      fallback={(children) => <div className={classes}>{children}</div>}
    >
      { image && 
        <img
          className={styles.Image}
          src={image}
          alt={title}
        />
      }
      <div className={styles.Info}>
        {children}
      </div>
    </ConditionalWrapper>
  )
}