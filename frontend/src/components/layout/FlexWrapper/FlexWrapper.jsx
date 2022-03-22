import styles from "./FlexWrapper.module.scss"

export const FlexWrapper = ({children, gap, row, column, wrap, justify, align, center, relative, className, container, ...otherProps}) => {
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
      data-container={container}
      className={className ? `${styles.BaseWrapper} ${className}` : styles.BaseWrapper}
      {...otherProps}
    >
      {children}
    </div>
  )
}