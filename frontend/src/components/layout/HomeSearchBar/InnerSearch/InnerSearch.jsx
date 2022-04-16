import { pt } from "date-fns/locale";
import { useFocusWithin } from "@react-aria/interactions";
import { useEffect, useState } from "react";
import {
  BaseButton,
  FlexWrapper,
  DropDownMenu,
  DestinationButton,
  CalendarWrapper,
} from "src/components";
import iconPin from "src/assets/icon-pin.png";
import iconCal from "src/assets/icon-calendar.png";
import { useWindowSize, useDestinations } from "src/hooks";

import styles from "./InnerSearch.module.scss";
import { useNavigate } from "react-router-dom";

export const InnerSearch = ({ className }) => {
  const size = useWindowSize();
  const navigate  = useNavigate();

  const [ranges, setRanges] = useState({
    startDate: new Date(),
    endDate: new Date(),
    key: "selection",
  });
  const { filteredDestinations, setDestinations, filterDestinations } = useDestinations()
  const [expandedCalendar, setExpandedCalendar] = useState(false);
  const [destinationFocus, toggleDestinationFocus] = useState(false);
  const [pinDropDown, setPinDropDown] = useState(false);
  const [destinationSearch, setDestinationSearch] = useState("");

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
    filterDestinations(destinationSearch);
    //eslint-disable-next-line
  }, [destinationSearch])

  useEffect(() => {
    setDestinations()
    //eslint-disable-next-line
  }, [])

  const selectDestination = () => {
    if(destinationSearch) {
      navigate(`/?destination=${destinationSearch}`)
    }
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
            value={destinationSearch}
            placeholder="Aonde vamos?"
            onChange={(e) => setDestinationSearch(e.target.value)}
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
                  setDestinationSearch(`${dest.city}, ${dest.country}`)
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
