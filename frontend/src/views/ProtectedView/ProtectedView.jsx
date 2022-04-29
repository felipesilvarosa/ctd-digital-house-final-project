import { useEffect } from "react"
import { useNavigate, useLocation } from "react-router"
import { useAuth } from "src/hooks"

export const ProtectedView = ({children, type}) => {
  const { user } = useAuth()
  const navigate = useNavigate()
  const location = useLocation()

  useEffect(() => {
    if (type === "USER" && (!user || !user.id)) {
      navigate(`/login?continue=${location.pathname}`)
    }

    if (type === "ADMIN" && (!user || user.role !== "ADMIN")) {
      navigate("/404")
    }
  }, [user, navigate, location.pathname])
  
  return (
    user && user.id ? children : null
  )
}