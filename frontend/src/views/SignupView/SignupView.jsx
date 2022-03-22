import { useEffect } from "react";
import { Link } from "react-router-dom";
import { useNavigate } from "react-router";
import { Form, Formik } from "formik"
import { useAuth } from "src/hooks";
import { InputGroup, BaseButton, FlexWrapper, FlashMessage } from "src/components"
import style from "./SignupView.module.scss"

export const SignupView = () => {

  const { user, registerUser, loading, signupErrors, clearSignupErrors } = useAuth()

  const navigate = useNavigate()

  const handleSubmit = async ({name, surname, email, password, passwordConfirmation}) =>{
    if (loading) return

    await registerUser({name, surname, email, password, passwordConfirmation})
  }

  useEffect(() => {
    if (user) {
      navigate("/")
    }
  }, [user, navigate])

    // eslint-disable-next-line
    useEffect(() => clearSignupErrors(), [])

  return(
    <>
      <div className={style.signup} data-testid="signup-view">
        <Formik initialValues={{name: '', surname: '', email: '', password: '', passwordConfirmation: ''}} onSubmit={handleSubmit}>
          <Form noValidate className={style.form}>
            <h2>Criar Conta</h2>

            { signupErrors.server && <FlashMessage type="error">{signupErrors.server}</FlashMessage> }

            <div className={style.nomeCompleto}>
              <InputGroup loading={loading} id="name" inputType="text" label="Nome" error={signupErrors.nome} />
              <InputGroup loading={loading} id="surname" inputType="text" label="Sobrenome" error={signupErrors.sobrenome} />
            </div>

            <InputGroup loading={loading} id="email" inputType="email" label="Email" error={signupErrors.email} />
            <InputGroup loading={loading} id="password" inputType="password" label="Senha" showPassword error={signupErrors.senha} />
            <InputGroup loading={loading} id="passwordConfirmation" inputType="password" label="Confirmar a senha" showPassword error={signupErrors.confirmarSenha} />

            <FlexWrapper row wrap align="center" className={style.ButtonLine}>
              <BaseButton type="submit">Cadastrar-se</BaseButton>
              <Link to="/login" className={style.link}>Já tenho uma conta ➜</Link>
            </FlexWrapper>

          </Form>
        </Formik>
      </div>
    </>
  )
}