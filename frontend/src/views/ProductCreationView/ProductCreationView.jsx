import { Form, Formik } from "formik"
import { useEffect, useState } from "react"
import { useNavigate } from "react-router"
import Swal from "sweetalert2"
import toast from "react-hot-toast"
import { 
  BaseButton,
  InputGroup,
  InputMultipleFileUpload, 
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
  const navigate = useNavigate()
  const { categories } = useCategories()
  const { availablePolicies, createNewProduct } = useProducts()
  const [ selectedCategory, setSelectedCategory ] = useState()
  const [ images, setImages ] = useState([])
  const [ formattedFormData, setFormattedFormData ] = useState({
    policiesIds: [],
    utilitiesNames: [],
    categoryId: null
  })
  const [ errors, setErrors ] = useState({})

  const addImages = (files) => {
    setImages(files)
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

  const handleSubmit = async (values) =>{

    const mandatoryFields = [ 
      "title", "description", "addressNumber", 
      "addressStreet", "addressCity", "addressState", "addressCountry"
    ]
    
    const scopedErrors = {}

    for (let field of mandatoryFields) {
      if (values[field].trim() === "") {
        scopedErrors[field] = "Campo obrigatório"
      }
    }

    if(!selectedCategory) {
      scopedErrors.category = "Escolha uma categoria"
    }

    setErrors(scopedErrors)

    if (Object.keys(scopedErrors).length > 0) {
      return
    }

    const address = [
      values.addressNumber, 
      values.addressStreet, 
      values.addressCity, 
      values.addressState, 
      values.addressCountry
    ].filter(value => value !== "").join(", ")

    const dataToSubmit = {
      ...formattedFormData,
      name: values.title,
      description: values.description,
      address: address,
    }

    const formData = new FormData()
    formData.append("dtoJSON", JSON.stringify(dataToSubmit))
    formData.append("images", [ ...document.querySelector("#files").files ])

    try {
      await createNewProduct(formData)
      Swal.fire(
        'Imóvel cadastrado!',
        'Você cadastrou um produto com sucesso.',
        "success"
      )
      navigate("/")
    } catch (e) {
      Swal.fire(
        'Aconteceu um erro...',
        "O seu cadastro não foi bem sucedido.",
        "error"
      )
    }
  }

  useEffect(() => {
    if(Object.keys(errors).length > 0) {
      window.scrollTo(0, 0)
    }
  }, [errors])

  useEffect(() => {
    setFormattedFormData({...formattedFormData, categoryId: selectedCategory})
    // eslint-disable-next-line
  }, [selectedCategory])

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
              { errors.category && <p className={styles.Error}>{errors.category}</p> }
            </section>

            <section className={styles.MainBlock}>
              <h2>Detalhes</h2> 
              <div className={styles.InputCluster}>
                <InputGroup id="title" inputType="text" label="Título" error={errors.title} />
              </div>

              <div className={styles.StreetCluster}>
                <InputGroup id="addressStreet" inputType="text" label="Rua" error={errors.addressStreet} />
                <InputGroup id="addressNumber" inputType="text" label="Número" error={errors.addressNumber} />
              </div>

              <div className={styles.InputCluster}>
                <InputGroup id="addressComplement" inputType="text" label="Complemento" />
              </div>

              <div className={styles.InputCluster}>
                <InputGroup id="addressCity" inputType="text" label="Cidade" error={errors.addressCity} />
                <InputGroup id="addressState" inputType="text" label="Estado" error={errors.addressState} />
                <InputGroup id="addressCountry" inputType="text" label="País" error={errors.addressCountry} />
              </div>

              <InputGroup id="description" inputType="textarea" label="Descrição" error={errors.description} />
            </section>

            <section className={styles.MainBlock}>
              <h2>Utilidades</h2>

              <div className={styles.UtilitiesBlock}>
                {
                  Object.entries(availableUtilities).map(utility => {
                    return <BaseTag key={utility[1]} variant="outline">
                      <BaseToggle id={utility[0]} onChange={e => handleToggle("utilitiesNames", utility[0], e.target.checked)}>
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
              <InputMultipleFileUpload 
                fileType="image"
                onUploadFail={toast.error}
                onUploadSuccess={addImages}
                id="files"
              />
              <div className={styles.ImageTags}>
                {
                  images.length > 0 && Object.entries(images).map(image => <BaseTag key={image[1].name}>{image[1].name}</BaseTag>)
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
