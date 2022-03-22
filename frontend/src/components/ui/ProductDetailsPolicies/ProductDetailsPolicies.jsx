import styles from "./ProductDetailsPolicies.module.scss"

export const ProductDetailsPolicies = ({policies}) => {
  return <>
    <section className={styles.Wrapper}>
      {
        policies && Object.entries(policies).map(policy => (
          <div key={policy[1].title}>
            <h3>{policy[1].title}</h3>
            <div>
              {
                typeof policy[1].description === "string" 
                  ? policy[1].description
                  : policy[1].description.map(item => (
                    <div key={item.description} className={styles.PolicyItem}>
                      <span className="material-icons">{item.icon}</span>
                      <p>{item.description}</p>
                    </div>
                  ))
              }
            </div>
          </div>
        ))
      }
    </section>
  </>
}