import styles from "./BaseTag.module.scss"

export const BaseTag = ({children, className, variant, ...otherProps}) => {
  const tagClasses = () => {
    const currClasses = [ styles.BaseTag ]
    if (variant === "outline") currClasses.push(styles.OutlineTag)
    if (className) currClasses.push(className)
    return currClasses.join(" ")
  }
  return <>
    <div className={tagClasses()} {...otherProps}>
      {children}
    </div>
  </>
}