import { BaseButton, CardsGridLoader, HomeCard, HomeSection, RatingBlock, StarsMeter } from "src/components"
import { Link, useSearchParams } from "react-router-dom"
import { useCategories, useProducts, useWindowSize } from "src/hooks";
import { getUtilityMaterialIconString, getRandomImage, getTruncatedString } from "src/utils";
import { useEffect, useState } from "react";
import styles from "./HomeRecomendations.module.scss";

export const HomeRecomendations = ({categoryId, destination}) => {
  const [ query ] = useSearchParams()
  const { searchProducts, products, loading } = useProducts();
  const { categories } = useCategories()
  const size = useWindowSize()

  const [ filteredProducts, setFilteredProducts ] = useState(products)
  const [ categoryName, setCategoryName ] = useState()

  useEffect(() => {
    searchProducts({
      destinationId: query.get("destinationId"),
      startDate: query.get("startDate"),
      endDate: query.get("endDate"),
      categoryId: query.get("categoryId")
    })
    // eslint-disable-next-line
  }, [query]);

  useEffect(() => {
    if(categoryName) {
      setFilteredProducts(products.filter(product => product.category === categoryName))
    } else {
      setFilteredProducts(products)
    }
  }, [categoryName, products, destination])

  useEffect(() => {
    const currentCategory = categories.find(c => `${c.id}` === `${categoryId}`)
    if (currentCategory) {
      setCategoryName(currentCategory.title)
    } else {
      setCategoryName(null)
    }
    // eslint-disable-next-line
  }, [categoryId])

  return (
    <HomeSection className={styles.Section}>
      <h2 className={styles.Title}>{ categoryName ? <span>{categoryName} <Link to="/">(Ver todas as categorias)</Link></span> : "Recomendações" }</h2>
      {
        loading
          ?
            <CardsGridLoader variant="recomendations" />
          : filteredProducts && filteredProducts.length > 0 
          ?
            <div className={styles.Grid}>
                { filteredProducts.map((product) => (
                  <HomeCard 
                    horizontal={size.width >= 520 }
                    key={product.id}
                    to={`/products/${product.id}`}
                    image={product.images[0] ?? getRandomImage()}
                    title={product.title}
                  >
                    <div className={styles.Header}>
                      <div>
                        <div className={styles.HeaderCategory}>
                          {product.category}
                          {product.stars && <StarsMeter value={product.stars} />}
                        </div>
                        <h3>{product.title}</h3>
                      </div>
                      <RatingBlock rating={product.rating} />
                    </div>
                    
                    <p className={styles.Location}>{product.destination}</p>
                    <div className={styles.Utilities}>
                      { product.utilities.map(utility => <span key={`${product.id} ${utility}`} className="material-icons" data-tooltip={utility}>{ getUtilityMaterialIconString(utility) }</span>)}
                    </div>
                    <p className={styles.Description}>{getTruncatedString(product.description)}... <Link to={`/products/${product.id}`}>mais</Link></p>
                    <BaseButton type="link" to={`/products/${product.id}`}>Ver mais</BaseButton>
                  </HomeCard>
                ))}
              </div>
          : <h2>Nenhum produto encontrado</h2>
      }
    </HomeSection>
  );
};
