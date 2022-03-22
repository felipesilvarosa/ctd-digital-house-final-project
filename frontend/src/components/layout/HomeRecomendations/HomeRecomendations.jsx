import { BaseButton, CardsGridLoader, HomeCard, HomeSection, RatingBlock, StarsMeter } from "src/components"
import { Link } from "react-router-dom"
import { useProducts, useWindowSize } from "src/hooks";
import { useEffect, useState } from "react";
import styles from "./HomeRecomendations.module.scss";

export const HomeRecomendations = ({category, destination}) => {
  const { setProducts, products, loading } = useProducts();
  const size = useWindowSize()

  const [ filteredProducts, setFilteredProducts ] = useState(products)

  useEffect(() => {
    setProducts()
    // eslint-disable-next-line
  }, []);

  useEffect(() => {
    if(category) {
      setFilteredProducts(products.filter(product => product.category === category))
    } else if(destination) {
      setFilteredProducts(products.filter(product => product.location === destination))
    } else {
      setFilteredProducts(products)
    }
  }, [category, products, destination])

  return (
    <HomeSection className={styles.Section}>
      <h2 className={styles.Title}>{ category ? <span>{category} <Link to="/">(Ver todas as categorias)</Link></span> : "Recomendações" }</h2>
      {
        loading
          ?
            <CardsGridLoader variant="recomendations" />
          :
            (
              <div className={styles.Grid}>
                {filteredProducts &&
                  filteredProducts.length > 0 &&
                  filteredProducts.map((product) => (
                    <HomeCard 
                      horizontal={size.width >= 520 }
                      key={product.id}
                      to={`/products/${product.id}`}
                      image={product.images[0]}
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
                      
                      <p className={styles.Location}>{product.location}</p>
                      <div className={styles.Utilities}>
                        <div>
                          <span className="material-icons">place</span>
                          {product.utilities.distance}
                        </div>
                        { product.utilities.wifi && <span className="material-icons">wifi</span> }
                        { product.utilities.swimming && <span className="material-icons">pool</span> }
                      </div>
                      <p className={styles.Description}>{product.description.match(/^.{80}\w*/)}... <Link to={`/products/${product.id}`}>mais</Link></p>
                      <BaseButton type="link" to={`/products/${product.id}`}>Ver mais</BaseButton>
                    </HomeCard>
                  ))}
              </div>
            )
      }
    </HomeSection>
  );
};
