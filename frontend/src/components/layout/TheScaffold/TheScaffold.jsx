import { useEffect, useCallback } from "react"
import { TheHeader, TheFooter } from "src/components"
import toast, { Toaster } from "react-hot-toast"
import { useCategories, useAuth } from "src/hooks"

export const TheScaffold = ({children}) => {
  const { setCategories } = useCategories()
  const { user, loginStatus, signupStatus, updateLoginStatus, updateSignupStatus } = useAuth()
  

  const notifyAndReset = useCallback((message, type) => {
    if (!type) {
      type = "success"
    }
    toast[type](message)
    updateLoginStatus(null)
    updateSignupStatus(null)
  }, [updateLoginStatus, updateSignupStatus])

  useEffect(() => {
    if(user && (loginStatus === "success" || signupStatus === "success")) {
      notifyAndReset(`Olá, ${user.fullName}! Você está logado agora.`)
    }

    if(loginStatus === "logout") {
      notifyAndReset("Você não está mais logado.")
    }
  }, [user, loginStatus, signupStatus, notifyAndReset])

  useEffect(() => {
    setCategories();
    // eslint-disable-next-line
  }, []);

  return (
    <>
      <TheHeader />
      <main data-testid="main">
        {children}
      </main>
      <Toaster 
        position="top-center"
        toastOptions={{
          style: {
            border: "1px solid #a6a6a6",
            boxShadow: "0 0.25rem 0.5rem var(--col-shadow-light)"
          }
        }}
      />
      <TheFooter />
    </>
  )
}