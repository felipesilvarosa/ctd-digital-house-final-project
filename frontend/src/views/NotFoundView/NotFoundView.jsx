import { Link } from "react-router-dom"
import { ResponsiveContainer, SpacingShim } from "src/components"
import styles from "./NotFoundView.module.scss"

export const NotFoundView = () => {
  return (
    <>
      <SpacingShim height="10rem" />
      <ResponsiveContainer>
        <h1>Página não encontrada</h1>
        <p className={styles.Paragraph}>A página que você está tentando acessar não existe, ou você não tem permissão para acessá-la. 🙁</p>
        <p className={styles.Paragraph}><Link to="/" className={styles.Link}>Clique aqui para retornar para a página principal.</Link></p>
      </ResponsiveContainer>
    </>
  )
}