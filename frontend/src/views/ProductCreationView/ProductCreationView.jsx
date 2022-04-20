import { Form, Formik } from "formik"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"
import toast from "react-hot-toast"
import { 
  BaseButton,
  InputGroup,
  InputFileUpload, 
  ResponsiveContainer,
  SpacingShim,
  BackButton,
  BaseTag,
  BaseToggle
} from "src/components"
import { useCategories, useProducts } from "src/hooks"
import { availableUtilities } from "src/utils"
import styles from "./ProductCreationView.module.scss"

export const ProductCreationView = () => {

  const { categories } = useCategories()
  const { availablePolicies } = useProducts()
  const [ selectedCategory, setSelectedCategory ] = useState()
  const [ images, setImages ] = useState([])
  const [ formattedFormData, setFormattedFormData ] = useState({
    name: "", 
    description: "", 
    categoryId: selectedCategory,
    rating: 10,
    address: "",
    stars: null,
    policiesIds: [],
    utilitiesIds: []
  })

  const addImage = (image) => {
    setImages(curr => [...curr, image])
  }

  const handleToggle = (type, id, value) => {
    if (value) {
      setFormattedFormData(curr => ({
        ...curr,
        [type]: [ ...curr[type], id]
      }))
    } else {
      setFormattedFormData(curr => ({
        ...curr,
        [type]: curr[type].filter(entry => entry !== id)
      }))
    }
  }

  const navigate = useNavigate()

  const handleSubmit = async (values) =>{
    const address = [
      values.addressNumber, 
      values.addressStreet, 
      values.addressCity, 
      values.addressState, 
      values.addressCountry
    ].filter(value => value !== "").join(", ")

    setFormattedFormData({
      ...formattedFormData,
      name: values.title,
      description: values.description,
      categoryId: selectedCategory,
      address: address,
    })
  }

  useEffect(() => {
    console.log(formattedFormData)
    // eslint-disable-next-line
  }, [formattedFormData])

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
          }} 
          onSubmit={(values) => handleSubmit(values)}>
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
                      <BaseToggle id={utility[0]} onChange={e => handleToggle("utilitiesIds", utility[0], e.target.checked)}>
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

            <section className={styles.MainBlock}>
              <h2>Regras da Casa</h2>

              <div className={styles.UtilitiesBlock}>
                {
                  availablePolicies
                    .filter(policy => policy.type === "rules")
                    .map(policy => {
                      return <BaseTag key={policy.id} variant="outline">
                        <BaseToggle id={policy.id} onChange={e => handleToggle("policiesIds", policy.id, e.target.checked)}>
                          <div className={styles.Utility}>
                            <span className="material-icons">{policy.icon}</span>
                            <p>{policy.description}</p>
                          </div>
                        </BaseToggle>
                      </BaseTag>
                    })
                }
              </div>
            </section>

            <section className={styles.MainBlock}>
              <h2>Saúde e Segurança</h2>

              <div className={styles.UtilitiesBlock}>
                {
                  availablePolicies
                    .filter(policy => policy.type === "safety")
                    .map(policy => {
                      return <BaseTag key={policy.id} variant="outline">
                        <BaseToggle id={policy.id} onChange={e => handleToggle("policiesIds", policy.id, e.target.checked)}>
                          <div className={styles.Utility}>
                            <span className="material-icons">{policy.icon}</span>
                            <p>{policy.description}</p>
                          </div>
                        </BaseToggle>
                      </BaseTag>
                    })
                }
              </div>
            </section>

            <section className={styles.MainBlock}>
              <h2>Políticas de Cancelamento</h2>

              <div className={styles.UtilitiesBlock}>
                {
                  availablePolicies
                    .filter(policy => policy.type === "canceling")
                    .map(policy => {
                      return <BaseTag key={policy.id} variant="outline">
                        <BaseToggle id={policy.id} onChange={e => handleToggle("policiesIds", policy.id, e.target.checked)}>
                          <div className={styles.Utility}>
                            <span className="material-icons">{policy.icon}</span>
                            <p>{policy.description}</p>
                          </div>
                        </BaseToggle>
                      </BaseTag>
                    })
                }
              </div>
            </section>

            <section className={styles.MainBlock}>
              <h2>Fotos</h2>
              <InputFileUpload 
                fileType="image"
                onUploadFail={toast.error}
                onUploadSuccess={addImage}
              />
              <div className={styles.ImageTags}>
                {
                  images.length > 0 && images.map(image => <BaseTag key={image.name}>{image.name}</BaseTag>)
                }
              </div>
            </section>

            <section className={styles.MainBlock}>
              <h2>Concluir cadastro</h2>
              <p>Após terminar de incluir os dados necessários, clique no botão abaixo para concluir o cadastro do produto.</p>
              <BaseButton type="submit">enviar</BaseButton>
            </section>
          </Form>
        </Formik>
      </ResponsiveContainer>
    </>
  )
}
