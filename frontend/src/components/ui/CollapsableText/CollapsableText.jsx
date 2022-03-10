import { useCallback, useEffect, useState } from "react"

export const CollapsableText = ({ text, ...otherProps }) => {
  const [ collapsed, setCollapsed ] = useState(true)
  const [ adjustedText, setAdjustedText ] = useState(text)

  const collapseText = useCallback(() => {
    setAdjustedText(text.match(/^.{80}\w*/))
  }, [text])

  const expandText = useCallback(() => {
    setAdjustedText(text)
  }, [text])

  useEffect(() => {
    if (!collapsed) {
      expandText()
    } else {
      collapseText()
    }
  }, [collapsed, expandText, collapseText])

  return <p {...otherProps}>{adjustedText}... <button onClick={() => setCollapsed(curr => !curr)}>{collapsed ? "mais" : "menos"}</button></p>
}