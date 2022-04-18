import { Form, Formik } from "formik"
import {  useNavigate } from "react-router"
import { BaseButton, FlexWrapper, InputGroup } from "src/components"
import styles from "./ProductCreationView.module.scss"

export const ProductCreationView = () => {

  const navigate = useNavigate()

  const handleSubmit = async () =>{

    // await funçãoDeCadastrarProduto()
  }

  const handleReset = () =>{

    navigate("/")
  }

  return(
    <>
      <div className={styles.create} data-testid="create-view">
        <h1>Criar Produto</h1>
        <Formik 
        initialValues={{
          title: '', 
          description: '', 
          category: '', 
          destination: '', 
          address: '', 
          latitude: '',
          longitude: '',
          images: [],
          utilities: [],
          policies: {}
          }} 
          onSubmit={handleSubmit} onReset={handleReset} >
          <Form noValidate className={styles.form}>

            <div className={styles.inputGroups}>
              <InputGroup  id="title" inputType="text" label="Título" />
              <InputGroup  id="category" inputType="text" label="Categoria" />
            </div>

            <div className={styles.inputGroups}>
              <InputGroup  id="address" inputType="text" label="Endreço" />
              <InputGroup  id="destination" inputType="text" label="Cidade" />
            </div>

            <div className={styles.inputGroups}>
              <InputGroup  id="latitude" inputType="text" label="Latitude" />
              <InputGroup  id="longitude" inputType="text" label="Longitude" />
            </div>

            <InputGroup  id="description" inputType="textarea" label="Descrição" />

            <h2>Adicionar Atributos</h2>

            <div className={styles.inputGroups}>
              <InputGroup  inputType="text" label="Atributo" />
              <InputGroup  id="icon" inputType="text" label="Icone" />
              <BaseButton >+</BaseButton>
            </div>

            <h2>Políticas do produto</h2>
            <FlexWrapper>
              <div className={styles.inputGroups}>
                <h3>Regras da Casa</h3>
                <p>Descrição</p>
                <textarea name="" id="" cols="30" rows="10" placeholder="Escreva aqui"></textarea>
              </div>
              <div className={styles.inputGroups}>
                <h3>Saúde e Segurança</h3>
                <p>Descrição</p>
                <textarea name="" id="" cols="30" rows="10" placeholder="Escreva aqui"></textarea>
              </div>
              <div className={styles.inputGroups}>
                <h3>Política de cancelamento</h3>
                <p>Descrição</p>
                <textarea name="" id="" cols="30" rows="10" placeholder="Escreva aqui"></textarea>
              </div>
            </FlexWrapper>

            <h2>Adicionar imagens</h2>
            <div className={styles.inputGroups}>
              <InputGroup  id="firstName" inputType="text" label="Atributo" />
              <InputGroup  id="lastName" inputType="text" label="Icone" />
              <button>+</button>
            </div>


            <FlexWrapper row wrap align="center" className={styles.ButtonLine}>
              <BaseButton type="reset">Cancelar</BaseButton>
              <BaseButton type="submit">Criar</BaseButton>
            </FlexWrapper>

          </Form>
        </Formik>
      </div>
    </>
  )
}
