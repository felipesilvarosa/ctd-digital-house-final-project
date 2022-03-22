import { MenuMobile, MenuDesktop, ConditionalWrapper } from "src/components"
import { useEffect, useState } from "react"
import { useWindowSize } from "src/hooks"

export const TheMenu = () => {
  const menuItems = [
    {
      id: "login",
      text: "Iniciar sessão",
      to: "/login"
    },
    {
      id: "signup",
      text: "Criar conta",
      to: "/signup"
    }
  ]

  const windowSize = useWindowSize()
  const [ mobile, toggleMobile ] = useState(windowSize.width < 500)

  useEffect(() => {
    toggleMobile(windowSize.width < 600)
  }, [toggleMobile, windowSize])

  return (
    <ConditionalWrapper 
      condition={mobile} 
      wrapper={() => <MenuMobile menuItems={menuItems} />} 
      fallback={() => <MenuDesktop menuItems={menuItems} />}
    />
  )
}