import styles from "./HomeSection.module.scss"
export const HomeSection = ({children, className}) => <section className={`${styles.Section} ${className}`}>{children}</section>