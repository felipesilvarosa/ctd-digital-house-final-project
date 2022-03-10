import { useEffect, useRef } from "react"
import styles from "./DropDownMenu.module.scss"

export const DropDownMenu = ({children, toggle, left, className, onClickOutside, onClickAnywhere, scrollable, ...otherProps}) => {

  const makeClasses = () => {
    const classesMap = [styles.Menu]
    if (className) {
      classesMap.push(className)
    }

    if (scrollable) {
      classesMap.push(styles.Scrollable)
    }

    return classesMap.join(" ")
  }

  const ref = useRef(null)

  useEffect(() => {
    const handleClickAnywhere = () => {
      toggle && onClickAnywhere && onClickAnywhere()
    };
    document.addEventListener('click', handleClickAnywhere);
    return () => {
      document.removeEventListener('click', handleClickAnywhere);
    };
  }, [ onClickAnywhere, toggle ]);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (ref.current && !ref.current.contains(event.target)) {
        toggle && onClickOutside && onClickOutside()
      }
    };
    document.addEventListener('click', handleClickOutside);
    return () => {
      document.removeEventListener('click', handleClickOutside);
    };
  }, [ onClickOutside, toggle ]);

  return (
    toggle && children
      ? <ul ref={ref} className={makeClasses()} data-left={left} {...otherProps}>
        {children}
      </ul>
      : null
  )
}