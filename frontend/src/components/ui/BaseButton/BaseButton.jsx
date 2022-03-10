import styles from "./BaseButton.module.scss"
export const BaseButton = ({children, variants, ...otherProps}) => {

  const buttonStyles = () => {
    let variantsString = ""
    if (typeof variants === "string") {
      variantsString = variants
    }

    const variantStyles = {
      blue: styles.Blue,
      red: styles.Red,
      black: styles.Black,
      highlight: styles.Highlight,
      outline: styles.Outline,
      link: styles.Link
    }

    const arrayOfVariants = variantsString.split(" ")
    const arrayOfStyles = [
      styles.Button,
      ...arrayOfVariants
        .map(variant => variantStyles[variant] || "")
        .filter(variant => variant !== "")
    ]

    return arrayOfStyles.join(" ")
  }

  return (
    <button className={buttonStyles()} {...otherProps}>
      {children}
    </button>
  )
}