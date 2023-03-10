import { useState, useEffect } from "react"
import { useSearchParams } from "react-router-dom";
import { CardsGridLoader, HomeCard, HomeSection } from "src/components";
import { useCategories } from "src/hooks";
import styles from "./HomeCategories.module.scss";

export const HomeCategories = () => {
  const { categories, loading } = useCategories()
  const [params] = useSearchParams()

  const [ queryString, setQueryString ] = useState("")

  useEffect(() => {
    let q = "?"
    for (let entry of params.entries()) {
      q += `${entry[0]}=${entry[1]}&`
    }
    q = q.replace(/&$/, "")
    setQueryString(q)
  }, [params])

  return (
    <HomeSection>
      <h2 className={styles.Title}>Buscar por tipo de acomodação</h2>
      { 
        loading
          ?
            <CardsGridLoader variant="categories" />
          :
            <div className={styles.CategoriesGrid}>
              {categories && categories.length > 0 &&
                categories.map((category) => (
                  category.productCount > 0 &&
                    <HomeCard 
                      clickable
                      key={category.id}
                      to={queryString ? `/${queryString}&categoryId=${category.id}` : `/?categoryId=${category.id}`}
                      image={category.imageUrl}
                      title={category.title}
                    >
                      <h3 className={styles.CategoryTitle}>
                        {category.title}
                      </h3>
                      <p className={styles.CategoryText}>
                        { category.productCount && <span>{Number(category.productCount).toLocaleString()} </span> }
                        { category.productCount ? category.title : category.description }
                      </p>
                    </HomeCard>
                ))}
            </div>
      }
    </HomeSection>
  );
};
