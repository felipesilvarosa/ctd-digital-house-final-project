import { 
  SpacingShim, 
  ResponsiveContainer, 
  BackButton, 
  ProductDetailsMap, 
  ProductDetailsGallery, 
  RatingBlock, 
  ProductDetailsUtilities,
  ProductDetailsPolicies,
  ProductDetailsAvailability
} from "src/components"
import { useParams } from "react-router"
import { useEffect } from "react"
import { useProducts } from "src/hooks"
import styles from "./ProductDetailsView.module.scss"

export const ProductDetailsView = () => {
  const params = useParams()
  const { findProductById, product, loading } = useProducts()


  useEffect(() => {
    findProductById(params.id)
    // eslint-disable-next-line
  }, [])

  return (
    <>
      <SpacingShim height="5rem" />
      <ResponsiveContainer className={styles.ProductDetails}>
        <BackButton>Voltar</BackButton>
        {
          loading ?
            <h1>Carregando...</h1>
          :
            <>
              <h1>{product.title}</h1>
              <h2>{product.category}</h2>
              <ProductDetailsGallery images={product.images} />
              <div className={styles.Rating}>
                <RatingBlock rating={product.rating} />
                <p>{product.description}</p>
              </div>
              <ProductDetailsUtilities utilities={product.utilities} />
              <ProductDetailsPolicies policies={product.policies} />
              <ProductDetailsAvailability unavailable={product.unavailable} />
              <ProductDetailsMap lon={product.longitude} lat={product.latitude} />
            </>
        }
      </ResponsiveContainer>
    </>
  )
}