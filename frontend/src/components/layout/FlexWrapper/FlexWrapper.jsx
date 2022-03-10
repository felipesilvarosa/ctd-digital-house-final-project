import styles from "./FlexWrapper.module.css"

export const FlexWrapper = ({children, gap, row, column, wrap, justify, align, center, relative, className, ...otherProps}) => {
  return (
    <div 
      style={{gap: gap ? `${gap}rem` : 0}} 
      data-relative={relative}
      data-row={row} 
      data-column={column}
      data-wrap={wrap}
      data-justify={justify}
      data-align={align}
      data-center={center}
      className={className ? `${styles.BaseWrapper} ${className}` : styles.BaseWrapper}
      {...otherProps}
    >
      {children}
    </div>
  )
}