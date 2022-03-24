import styles from "./ResponsiveContainer.module.scss"
export const ResponsiveContainer = ({children, className, ...props}) => {
  const classes = [
    styles.Container,
    className
  ].join(" ")
  return <div className={classes} {...props}>{children}</div>
}