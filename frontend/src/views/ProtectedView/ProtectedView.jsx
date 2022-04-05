import { useEffect } from "react"
import { useNavigate, useLocation } from "react-router"
import { useAuth } from "src/hooks"

export const ProtectedView = ({children}) => {
  const { user } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()

  useEffect(() => {
    if (!user || !user.id) {
      navigate(`/login?continue=${location.pathname}`)
    }
  }, [user, navigate, location.pathname])
  
  return (
    user && user.id ? children : null
  )
}