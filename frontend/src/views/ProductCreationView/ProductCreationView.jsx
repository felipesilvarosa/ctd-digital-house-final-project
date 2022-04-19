import { Form, Formik } from "formik"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"
import { 
  BaseButton, 
  FlexWrapper, 
  InputGroup, 
  ResponsiveContainer,
  SpacingShim,
  BackButton,
  BaseTag,
  BaseToggle
} from "src/components"
import { useCategories } from "src/hooks"
import { availableUtilities } from "src/utils"
import styles from "./ProductCreationView.module.scss"

export const ProductCreationView = () => {

  const { categories } = useCategories()
  const [ selectedCategory, setSelectedCategory ] = useState()

  const navigate = useNavigate()

  const handleSubmit = async () =>{

    // await funçãoDeCadastrarProduto()
  }

  const handleReset = () =>{

    navigate("/")
  }

  useEffect(() => {
    console.log(categories)
    // eslint-disable-next-line
  }, [])

  return(
    <>
      <SpacingShim height="5rem" />
      <ResponsiveContainer data-testid="create-view">
        <BackButton to="/">Voltar</BackButton>
        <h1>Cadastrar Produto</h1>
        <Formik 
          initialValues={{
            title: '', 
            description: '', 
            category: '', 
            addressStreet: '',
            addressNumber: '',
            addressComplement: '',
            addressCity: '',
            addressState: '',
            addressCountry: '',
            images: [],
            utilities: [],
            policies: {}
          }} 
          onSubmit={handleSubmit} onReset={handleReset} >
          <Form noValidate className={styles.Form}>

            <section className={styles.MainBlock}>
              <h2>O que você quer cadastrar?</h2>
              {
                categories.map(category => <BaseTag key={category.id} className={selectedCategory === category.id && styles.Selected} onClick={() => setSelectedCategory(category.id)}>{category.title}</BaseTag>)
              }
            </section>

            <section className={styles.MainBlock}>
              <h2>Detalhes</h2> 
              <div className={styles.InputCluster}>
                <InputGroup id="title" inputType="text" label="Título" />
              </div>

              <div className={styles.StreetCluster}>
                <InputGroup id="addressStreet" inputType="text" label="Rua" />
                <InputGroup id="addressNumber" inputType="text" label="Número" />
              </div>

              <div className={styles.InputCluster}>
                <InputGroup id="addressComplement" inputType="text" label="Complemento" />
              </div>

              <div className={styles.InputCluster}>
                <InputGroup id="addressCity" inputType="text" label="Cidade" />
                <InputGroup id="addressState" inputType="text" label="Estado" />
                <InputGroup id="addressCountry" inputType="text" label="País" />
              </div>

              <InputGroup id="description" inputType="textarea" label="Descrição" />
            </section>

            <section className={styles.MainBlock}>
              <h2>Utilidades</h2>

              <div className={styles.UtilitiesBlock}>
                {
                  Object.entries(availableUtilities).map(utility => {
                    return <BaseTag key={utility[1]} variant="outline">
                      <BaseToggle id={utility[0]}>
                        <div className={styles.Utility}>
                          <span className="material-icons">{utility[1]}</span>
                          <p>{utility[0]}</p>
                        </div>
                      </BaseToggle>
                    </BaseTag>
                  })
                }
              </div>
            </section>

            {/* <h2>Políticas do produto</h2>
            <FlexWrapper>
              <div className={styles.InputCluster}>
                <h3>Regras da Casa</h3>
                <p>Descrição</p>
                <textarea name="" id="" cols="30" rows="10" placeholder="Escreva aqui"></textarea>
              </div>
              <div className={styles.InputCluster}>
                <h3>Saúde e Segurança</h3>
                <p>Descrição</p>
                <textarea name="" id="" cols="30" rows="10" placeholder="Escreva aqui"></textarea>
              </div>
              <div className={styles.InputCluster}>
                <h3>Política de cancelamento</h3>
                <p>Descrição</p>
                <textarea name="" id="" cols="30" rows="10" placeholder="Escreva aqui"></textarea>
              </div>
            </FlexWrapper>

            <h2>Adicionar imagens</h2>
            <div className={styles.InputCluster}>
              <InputGroup id="firstName" inputType="text" label="Atributo" />
              <InputGroup id="lastName" inputType="text" label="Icone" />
              <button>+</button>
            </div>


            <FlexWrapper row wrap align="center" className={styles.ButtonLine}>
              <BaseButton type="reset">Cancelar</BaseButton>
              <BaseButton type="submit">Criar</BaseButton>
            </FlexWrapper> */}

          </Form>
        </Formik>
      </ResponsiveContainer>
    </>
  )
}
