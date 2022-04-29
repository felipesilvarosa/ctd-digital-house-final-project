import { useEffect } from "react";
import { Form, Formik } from "formik"
import { Link } from "react-router-dom";
import { useNavigate } from "react-router";
import { useSearchParams } from "react-router-dom";

import { InputGroup, BaseButton, FlexWrapper, FlashMessage } from "src/components";
import { useAuth } from "src/hooks"
import style from "./LoginView.module.scss"

export const LoginView = () => {
  const [query] = useSearchParams()
  const navigate = useNavigate()
  const {loading, user, loginErrors, userLogin, clearLoginErrors} = useAuth()

  const handleSubmit = async ({email, password}) =>{
    if (loading) return
    await userLogin(email, password)
  }

  useEffect(() => {
    const redirectUrl = query.get("continue") ?? "/"
    if(user) {
      navigate(redirectUrl)
    }
  }, [user, navigate, query])

  // eslint-disable-next-line
  useEffect(() => clearLoginErrors(), [clearLoginErrors])

  return (
    <>
      <div className={style.login} data-testid="login-view">
        <Formik initialValues={{email: '', password: ''}} onSubmit={handleSubmit}>
          <Form noValidate data-testid="form" className={style.form}>
            <h2>Iniciar sessão</h2>

            { loginErrors.server && <FlashMessage type="error">{loginErrors.server}</FlashMessage> }

            <InputGroup id="email" inputType="email" label="Email" loading={loading} error={loginErrors.email} />
            <InputGroup id="password" inputType="password" label="Senha" showPassword loading={loading} error={loginErrors.senha} />
            
            <FlexWrapper row wrap align="center" className={style.ButtonLine}>
              <BaseButton type="submit">Login</BaseButton>
              <Link to="/signup" className={style.link}>Ainda não sou cliente ➜</Link>
            </FlexWrapper>
            
          </Form>
        </Formik>
      </div>
    </>
  )
}