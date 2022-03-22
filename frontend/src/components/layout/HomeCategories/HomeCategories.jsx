import { CardsGridLoader, HomeCard, HomeSection } from "src/components";
import { useCategories } from "src/hooks";
import styles from "./HomeCategories.module.scss";

export const HomeCategories = () => {
  const { categories, loading } = useCategories()

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
                  <HomeCard 
                    clickable
                    key={category.id}
                    to={`/?category=${category.title}`}
                    image={category.image}
                    title={category.title}
                  >
                    <h3 className={styles.CategoryTitle}>
                      {category.title}
                    </h3>
                    <p className={styles.CategoryText}>
                      <span>{Number(category.entries).toLocaleString()}</span> {category.title}
                    </p>
                  </HomeCard>
                ))}
            </div>
      }
    </HomeSection>
  );
};
