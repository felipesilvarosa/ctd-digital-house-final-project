import { useState } from "react"
import { makeInitials } from "src/utils"
import styles from "./UserAvatar.module.css"
export const UserAvatar = (props) => {
  
  const [ initials ] = useState(makeInitials(props.fullName))
  
  return (
    <div className={styles.UserAvatar} data-testid="user-avatar">{initials}</div>
  )
}