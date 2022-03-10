import styles from "./RatingBlock.module.scss"
export const RatingBlock = ({ rating }) => {
  const ratingText = new Map([
    [1, "Terrível"],
    [2, "Horroroso"],
    [3, "Muito ruim"],
    [4, "Ruim"],
    [5, "Mediano"],
    [6, "Bom"],
    [7, "Muito bom"],
    [8, "Excelente"],
    [9, "Esplêndido"],
    [10, "Formidável"],
  ])
  
  return (
    <div className={styles.RatingBlock}>
      <p>{rating ?? "-"}</p>
      <p>{ratingText.get(rating) ?? "Sem avaliação"}</p>
    </div>
  )
}