import styles from "./ProductDetailsPolicies.module.scss"

export const ProductDetailsPolicies = ({policies, className, title}) => {
  return <>
    <section className={className ? `${styles.PoliciesContainer} ${className}` : styles.PoliciesContainer}>
      { title && <h1 className={styles.Title}>{title}</h1> }
      <div className={styles.Wrapper}>
        {
          policies && Object.entries(policies)
            .sort((a, b) => a[1].order - b[1].order)
            .map(policy => (
              <div key={policy[1].title}>
                <h3>{policy[1].title}</h3>
                <div>
                  {
                    policy[1].descriptions
                      .map(item => (
                        <div key={item.description} className={styles.PolicyItem}>
                          { item.icon && <span className="material-icons">{item.icon}</span> }
                          <p>{item.description}</p>
                        </div>
                    ))
                  }
                </div>
              </div>
          ))
        }
      </div>
    </section>
  </>
}