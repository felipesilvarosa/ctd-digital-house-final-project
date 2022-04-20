import { useState, useEffect } from "react"
import { generateRandomId } from "src/utils"
import { BaseButton } from "src/components"
import styles from "./InputFileUpload.module.scss"

export const InputFileUpload = ({id, fileType, onUploadSuccess = () => {}, onUploadFail = () => {}}) => {
  const inputId = id ?? generateRandomId()

  const [file, setFile] = useState(null)

  useEffect(() => {
    if (file && !file.type.includes(fileType)) {
      setFile(null)
      onUploadFail("Formato de arquivo inválido.")
    } else if (file && file.type.includes(fileType)) {
      onUploadSuccess(file)
    }
  }, [file])

  return <>
    <div className={styles.FileUpload}>
      <input type="file" id={inputId} onChange={(e => setFile(e.currentTarget.files[0]))} />
      <label htmlFor={inputId}>
        <BaseButton>escolher arquivo</BaseButton>
        <span>{ file ? file.name : "Nome do arquivo selecionado..." }</span>
      </label>
    </div>
  </>
}