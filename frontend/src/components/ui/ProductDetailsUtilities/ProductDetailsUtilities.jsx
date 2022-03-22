import styles from "./ProductDetailsUtilities.module.scss"

export const ProductDetailsUtilities = ({utilities}) => {
  
  const icons = {
    distance: {
      component: () => <span className="material-icons">place</span>,
      description: utilities && utilities.distance
    },
    wifi: {
      component: () => <span className="material-icons">wifi</span>,
      description: "Wi-Fi"
    },
    swimming: {
      component: () => <span className="material-icons">pool</span>,
      description: "Piscina"
    }
  }

  return <>
    <section className={styles.Utilities}>
      <h2>O que este lugar oferece?</h2>
      <div className={styles.Wrapper}>
        {
          utilities && Object.entries(utilities).map(utility => (
            <div key={utility[0]} className={styles.Tag}>
              {icons[utility[0]].component()}
              <p>{icons[utility[0]].description}</p>
            </div>
          ))
        }
      </div>
    </section>
  </>
}