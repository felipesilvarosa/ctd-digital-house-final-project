import { DateRange } from "react-date-range";
import "react-date-range/dist/styles.css";
import "react-date-range/dist/theme/default.css";
import "./CalendarWrapper.scss"

export const CalendarWrapper = ({...props}) => {
  return <DateRange {...props} />
}