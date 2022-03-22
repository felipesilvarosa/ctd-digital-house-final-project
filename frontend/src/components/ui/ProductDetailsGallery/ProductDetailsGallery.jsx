import { useState } from "react"
import { BaseButton, BaseModal } from "src/components"
import { sliceIntoChunks } from "src/utils"
import { useWindowSize } from "src/hooks"
import styles from "./ProductDetailsGallery.module.scss"


export const ProductDetailsGallery = ({images}) => {
  const { width } = useWindowSize()
  const [ showModal, toggleShowModal ] = useState(false)
  const toggleModal = () => {
    document.body.setAttribute("data-modalopen", !showModal)
    toggleShowModal(curr => !curr)
  }

  const mappedImages = images && images.map(image => <div key={image}><img src={image} key={image} alt="" /></div>)
  const imagesInColumns = images && sliceIntoChunks(images, 2)

  return (
    <>
      { 
        showModal && width > 500 && 
          <BaseModal toggleModal={toggleModal}>
            <div className={styles.ModalContent}>
              { 
                imagesInColumns.map(column => (
                  <div className={styles.ImageColumn} key={column[0]}>
                    {
                      column.map(image => <img key={image} src={image} alt="" />)
                    }
                  </div>
                ))
              }
            </div>
          </BaseModal>
      }

      <div className={styles.GalleryWrapper}>
        <div className={styles.GalleryDesktop}>{ mappedImages?.slice(0,5) }</div>
        <BaseButton onClick={toggleModal}>Ver mais</BaseButton>
      </div>
    </>
  )
}