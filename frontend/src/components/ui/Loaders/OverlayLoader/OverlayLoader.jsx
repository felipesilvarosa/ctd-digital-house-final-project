import { useEffect } from "react"
import styles from "./OverlayLoader.module.scss"

export const OverlayLoader = () => {
  useEffect(() => {
    document.body.setAttribute("data-modalopen", true)
    return () => document.body.setAttribute("data-modalopen", false)
  }, [])
  return <>
    <div className={styles.Loader}></div>
  </>
}