import { useEffect, useState } from "react";
import { pt } from "date-fns/locale";
import { useFocusWithin } from "@react-aria/interactions";
import { useNavigate, useSearchParams } from "react-router-dom";
import toast from "react-hot-toast"
import {
  BaseButton,
  FlexWrapper,
  DropDownMenu,
  DestinationButton,
  CalendarWrapper,
} from "src/components";
import { useWindowSize, useDestinations, useProducts } from "src/hooks";
import { getNumberWithTrailingZero } from "src/utils"
import iconPin from "src/assets/icon-pin.png";
import iconCal from "src/assets/icon-calendar.png";

import styles from "./InnerSearch.module.scss";

export const InnerSearch = ({ className }) => {
  const size = useWindowSize();
  const navigate  = useNavigate();
  const [ query ] = useSearchParams()

  const [ranges, setRanges] = useState({
    startDate: new Date(),
    endDate: new Date(),
    key: "selection",
  });
  const { filteredDestinations, setDestinations, filterDestinations } = useDestinations()
  const { searchProducts } = useProducts()
  const [expandedCalendar, setExpandedCalendar] = useState(false);
  const [destinationFocus, toggleDestinationFocus] = useState(false);
  const [pinDropDown, setPinDropDown] = useState(false);
  const [destinationSearch, setDestinationSearch] = useState({string: ""});

  const { focusWithinProps } = useFocusWithin({
    onFocusWithin: () => setExpandedCalendar(true),
    onBlurWithin: () => setExpandedCalendar(false),
    onFocusWithinChange: (isFocusWithin) => setExpandedCalendar(isFocusWithin),
  });

  const handleSelect = (range) => {
    setRanges(range.selection);
  };

  useEffect(() => {
    setPinDropDown(destinationFocus);
  }, [destinationFocus, setPinDropDown]);

  useEffect(() => {
    filterDestinations(destinationSearch.string);
    //eslint-disable-next-line
  }, [destinationSearch])

  useEffect(() => {
    setDestinations()
    //eslint-disable-next-line
  }, [])

  const selectDestination = () => {
    if(!destinationSearch.string || !ranges.startDate || !ranges.endDate) {
      return toast.error("Para buscar, preencha o destino, a data de check-in e a data de check-out.")
    } 

    const startDateString = `${ranges.startDate.getFullYear()}-${getNumberWithTrailingZero(ranges.startDate.getMonth()+1)}-${getNumberWithTrailingZero(ranges.startDate.getDate())}`
    const endDateString = `${ranges.endDate.getFullYear()}-${getNumberWithTrailingZero(ranges.endDate.getMonth()+1)}-${getNumberWithTrailingZero(ranges.endDate.getDate())}`

    const queryStringParams = {
      destinationId: destinationSearch.id,
      startDate: startDateString,
      endDate: endDateString,
      categoryId: query.get("categoryId")
    }

    navigate("/?" + Object.entries(queryStringParams).filter(param => param[1] !== null && param[1] !== "null").map(param => `${param[0]}=${param[1]}`).join("&"))
  }

  return (
    <FlexWrapper row wrap className={`${styles.BaseWrapper} ${className}`}>
      <FlexWrapper
        row
        relative
        className={`${styles.InputOuterWrapper} ${styles.Destination}`}
      >
        <div className={styles.InputWrapper}>
          <img src={iconPin} alt="pin de local" />
          <input
            type="text"
            value={destinationSearch.string}
            placeholder="Aonde vamos?"
            onChange={(e) => setDestinationSearch(curr => ({...curr, string: e.target.value}))}
            autoComplete="off"
            id="destination"
            name="destination"
            data-testid="destination-field"
            onFocus={() => toggleDestinationFocus(true)}
            onBlur={() => toggleDestinationFocus(false)}
          />
        </div>

        <DropDownMenu
          left
          scrollable
          toggle={pinDropDown}
          className={styles.PinDropDown}
          data-testid="destination-dropdown"
        >
          {filteredDestinations.map((dest) => (
            <li key={dest.id}>
              <DestinationButton
                city={dest.city}
                country={dest.country}
                onMouseDown={() =>
                  setDestinationSearch({
                    id: dest.id,
                    string: `${dest.city}, ${dest.country}`
                  })
                }
              />
            </li>
          ))}
        </DropDownMenu>
      </FlexWrapper>

      <FlexWrapper
        row
        relative
        className={`${styles.InputOuterWrapper} ${styles.Dates}`}
        {...focusWithinProps}
      >
        <div className={styles.InputWrapper} data-expanded={expandedCalendar}>
          <img src={iconCal} alt="calendário" />

          <CalendarWrapper
            locale={pt}
            ranges={[ranges]}
            onChange={handleSelect}
            dateDisplayFormat="d/MMM/yyyy"
            months={2}
            minDate={new Date()}
            direction={size.width > 800 ? "horizontal" : "vertical"}
            weekdayDisplayFormat={size.width > 800 ? "E" : "EEEEE"}
            onFocus={() => setExpandedCalendar((curr) => !curr)}
          />
        </div>
      </FlexWrapper>
      
      <BaseButton onClick={selectDestination}>Buscar</BaseButton>
    </FlexWrapper>
  );
};
